package com.biblioteca.back.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.biblioteca.back.converter.LibroConverter;
import com.biblioteca.back.entity.LibroEntity;
import com.biblioteca.back.repository.LibroRepository;
import com.biblioteca.back.service.LibroService;
import com.biblioteca.back.vo.EjemplarVO;
import com.biblioteca.back.vo.LibroVO;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final LibroConverter libroConverter;
    private final RestTemplate restTemplate;

    public LibroServiceImpl(LibroRepository libroRepository, LibroConverter libroConverter) {
        this.libroRepository = libroRepository;
        this.libroConverter = libroConverter;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<LibroVO> obtenerTodos() {
        return libroRepository.findAll().stream()
                .map(libroConverter::toVO)
                .collect(Collectors.toList());
    }

   
    @Override
    public List<LibroVO> sincronizarDesdeGoogleBooks(String query) {
        int startIndex = 0;
        int maxResults = 20;
        int totalMax = 60; 
        int totalFetched = 0;

        List<LibroVO> librosEncontrados = new ArrayList<>();

        while (totalFetched < totalMax) {
            String url = "https://www.googleapis.com/books/v1/volumes?q=" + query.replace(" ", "+")
                       + "&startIndex=" + startIndex + "&maxResults=" + maxResults;

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

            if (items == null || items.isEmpty()) break;

            for (Map<String, Object> item : items) {
                Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo");

                String titulo = (String) volumeInfo.get("title");
                if (titulo == null || titulo.length() > 60) continue;
                
                List<String> generos = (List<String>) volumeInfo.get("categories");
                String genero = (generos != null && !generos.isEmpty()) ? generos.get(0) : "Desconocido";


                List<String> autores = (List<String>) volumeInfo.get("authors");
                String autor = (autores != null && !autores.isEmpty()) ? autores.get(0) : "Desconocido";
                String editorial = (String) volumeInfo.getOrDefault("publisher", "Desconocida");
                String fecha = (String) volumeInfo.getOrDefault("publishedDate", "");
                Integer anioPublicacion = null;
                if (fecha != null && fecha.length() >= 4) {
                    String posibleAnio = fecha.substring(0, 4);
                    if (posibleAnio.matches("\\d{4}")) {
                        anioPublicacion = Integer.parseInt(posibleAnio);
                    }
                }

                List<Map<String, String>> identificadores = (List<Map<String, String>>) volumeInfo.get("industryIdentifiers");
                String isbn = null;
                if (identificadores != null) {
                    for (Map<String, String> id : identificadores) {
                        if ("ISBN_13".equals(id.get("type"))) {
                            isbn = id.get("identifier");
                            break;
                        }
                    }
                }

                String imagenUrl = null;
                Map<String, String> imagenes = (Map<String, String>) volumeInfo.get("imageLinks");
                if (imagenes != null) {
                    if (imagenes.containsKey("extraLarge")) {
                        imagenUrl = imagenes.get("extraLarge");
                    } else if (imagenes.containsKey("large")) {
                        imagenUrl = imagenes.get("large");
                    } else if (imagenes.containsKey("medium")) {
                        imagenUrl = imagenes.get("medium");
                    } else if (imagenes.containsKey("thumbnail")) {
                        imagenUrl = imagenes.get("thumbnail");
                    } else if (imagenes.containsKey("smallThumbnail")) {
                        imagenUrl = imagenes.get("smallThumbnail");
                    }
                }

                LibroVO libroVO = new LibroVO();
                libroVO.setTitulo(titulo);
                libroVO.setAutor(autor);
                libroVO.setEditorial(editorial);
                libroVO.setGenero(genero); 
                libroVO.setAnioPublicacion(anioPublicacion);
                libroVO.setIsbn(isbn);
                libroVO.setImagenUrl(imagenUrl);

                librosEncontrados.add(libroVO);

                totalFetched++;
            }

            startIndex += maxResults;
        }

        return librosEncontrados;
    }

	
	@Override
	public LibroVO guardarLibro(LibroVO libroVO) {
	    if (libroVO.getIsbn() != null && libroRepository.findByIsbn(libroVO.getIsbn()).isPresent()) {
	        throw new RuntimeException("Ya existe un libro con ese ISBN");
	    }
	    LibroEntity libro = libroConverter.toEntity(libroVO);
	    LibroEntity libroGuardado = libroRepository.save(libro);
	    return libroConverter.toVO(libroGuardado);
	}

	@Override
	public LibroVO findById(Long id) {

		LibroEntity entity = libroRepository.findById(id).orElse(null);
		return  (entity != null) ? libroConverter.toVO(entity) : null;
	}

    
    

}


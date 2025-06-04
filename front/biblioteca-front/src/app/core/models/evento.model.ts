export interface Evento {
  id?: number;
  nombre: string;
  descripcion?: string;
  fecha: string; // en formato ISO (ej: '2025-06-04')
  idEmpleadoOrganizador: number;
  nombreEmpleadoOrganizador?: string;
  idAsistentes?: number[];
  numeroAsistentes?: number;
}

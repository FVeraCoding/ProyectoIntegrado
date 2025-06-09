export interface ReservaId {
  socioId: number;
  ejemplarId: number;
}

export interface Reserva {
  reservaID?: ReservaId;
  socioID: number;
  ejemplarID: number;
  nombreSocio: string;
  nombreLibro: string;
  fechaInicio: string; 
  fechaFin: string;
}

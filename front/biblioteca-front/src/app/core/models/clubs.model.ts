export interface Club {
    id?: number;
    nombre: string;
    descripcion: string;
    fechaCreacion?: Date;
    nombreEmpleadoOrganizador?: string;
    idEmpleadoOrganizador: number;
    sociosId: number[];

}
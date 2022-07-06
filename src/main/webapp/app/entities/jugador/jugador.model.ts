import dayjs from 'dayjs/esm';

export interface IJugador {
  id?: number;
  apodo?: string;
  nombre?: string;
  apellido?: string;
  nacimiento?: dayjs.Dayjs;
}

export class Jugador implements IJugador {
  constructor(
    public id?: number,
    public apodo?: string,
    public nombre?: string,
    public apellido?: string,
    public nacimiento?: dayjs.Dayjs
  ) {}
}

export function getJugadorIdentifier(jugador: IJugador): number | undefined {
  return jugador.id;
}

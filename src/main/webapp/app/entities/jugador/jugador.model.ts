import dayjs from 'dayjs/esm';
import { IPartida } from 'app/entities/partida/partida.model';

export interface IJugador {
  id?: number;
  apodo?: string;
  nombre?: string;
  apellido?: string;
  nacimiento?: dayjs.Dayjs;
  ganadas?: IPartida[] | null;
  perdidas?: IPartida[] | null;
}

export class Jugador implements IJugador {
  constructor(
    public id?: number,
    public apodo?: string,
    public nombre?: string,
    public apellido?: string,
    public nacimiento?: dayjs.Dayjs,
    public ganadas?: IPartida[] | null,
    public perdidas?: IPartida[] | null
  ) {}
}

export function getJugadorIdentifier(jugador: IJugador): number | undefined {
  return jugador.id;
}

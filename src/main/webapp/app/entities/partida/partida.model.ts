import { IJugador } from 'app/entities/jugador/jugador.model';

export interface IPartida {
  id?: number;
  puntos?: number;
  juego?: string;
  ganador?: IJugador | null;
  perdedor?: IJugador | null;
}

export class Partida implements IPartida {
  constructor(
    public id?: number,
    public puntos?: number,
    public juego?: string,
    public ganador?: IJugador | null,
    public perdedor?: IJugador | null
  ) {}
}

export function getPartidaIdentifier(partida: IPartida): number | undefined {
  return partida.id;
}

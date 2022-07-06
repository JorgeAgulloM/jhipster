export interface IPartida {
  id?: number;
  puntos?: number;
  juego?: string;
}

export class Partida implements IPartida {
  constructor(public id?: number, public puntos?: number, public juego?: string) {}
}

export function getPartidaIdentifier(partida: IPartida): number | undefined {
  return partida.id;
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartida } from '../partida.model';
import { PartidaService } from '../service/partida.service';
import { PartidaDeleteDialogComponent } from '../delete/partida-delete-dialog.component';

@Component({
  selector: 'jhi-partida',
  templateUrl: './partida.component.html',
})
export class PartidaComponent implements OnInit {
  partidas?: IPartida[];
  isLoading = false;

  constructor(protected partidaService: PartidaService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partidaService.query().subscribe({
      next: (res: HttpResponse<IPartida[]>) => {
        this.isLoading = false;
        this.partidas = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartida): number {
    return item.id!;
  }

  delete(partida: IPartida): void {
    const modalRef = this.modalService.open(PartidaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partida = partida;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPartida, Partida } from '../partida.model';
import { PartidaService } from '../service/partida.service';
import { IJugador } from 'app/entities/jugador/jugador.model';
import { JugadorService } from 'app/entities/jugador/service/jugador.service';

@Component({
  selector: 'jhi-partida-update',
  templateUrl: './partida-update.component.html',
})
export class PartidaUpdateComponent implements OnInit {
  isSaving = false;

  jugadorsSharedCollection: IJugador[] = [];

  editForm = this.fb.group({
    id: [],
    puntos: [null, [Validators.required]],
    juego: [null, [Validators.required]],
    ganador: [],
    perdedor: [],
  });

  constructor(
    protected partidaService: PartidaService,
    protected jugadorService: JugadorService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partida }) => {
      this.updateForm(partida);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partida = this.createFromForm();
    if (partida.id !== undefined) {
      this.subscribeToSaveResponse(this.partidaService.update(partida));
    } else {
      this.subscribeToSaveResponse(this.partidaService.create(partida));
    }
  }

  trackJugadorById(_index: number, item: IJugador): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartida>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(partida: IPartida): void {
    this.editForm.patchValue({
      id: partida.id,
      puntos: partida.puntos,
      juego: partida.juego,
      ganador: partida.ganador,
      perdedor: partida.perdedor,
    });

    this.jugadorsSharedCollection = this.jugadorService.addJugadorToCollectionIfMissing(
      this.jugadorsSharedCollection,
      partida.ganador,
      partida.perdedor
    );
  }

  protected loadRelationshipsOptions(): void {
    this.jugadorService
      .query()
      .pipe(map((res: HttpResponse<IJugador[]>) => res.body ?? []))
      .pipe(
        map((jugadors: IJugador[]) =>
          this.jugadorService.addJugadorToCollectionIfMissing(
            jugadors,
            this.editForm.get('ganador')!.value,
            this.editForm.get('perdedor')!.value
          )
        )
      )
      .subscribe((jugadors: IJugador[]) => (this.jugadorsSharedCollection = jugadors));
  }

  protected createFromForm(): IPartida {
    return {
      ...new Partida(),
      id: this.editForm.get(['id'])!.value,
      puntos: this.editForm.get(['puntos'])!.value,
      juego: this.editForm.get(['juego'])!.value,
      ganador: this.editForm.get(['ganador'])!.value,
      perdedor: this.editForm.get(['perdedor'])!.value,
    };
  }
}

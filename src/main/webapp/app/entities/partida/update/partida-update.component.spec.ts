import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartidaService } from '../service/partida.service';
import { IPartida, Partida } from '../partida.model';

import { PartidaUpdateComponent } from './partida-update.component';

describe('Partida Management Update Component', () => {
  let comp: PartidaUpdateComponent;
  let fixture: ComponentFixture<PartidaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partidaService: PartidaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartidaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PartidaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartidaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partidaService = TestBed.inject(PartidaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const partida: IPartida = { id: 456 };

      activatedRoute.data = of({ partida });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partida));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Partida>>();
      const partida = { id: 123 };
      jest.spyOn(partidaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partida });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partida }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partidaService.update).toHaveBeenCalledWith(partida);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Partida>>();
      const partida = new Partida();
      jest.spyOn(partidaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partida });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partida }));
      saveSubject.complete();

      // THEN
      expect(partidaService.create).toHaveBeenCalledWith(partida);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Partida>>();
      const partida = { id: 123 };
      jest.spyOn(partidaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partida });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partidaService.update).toHaveBeenCalledWith(partida);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

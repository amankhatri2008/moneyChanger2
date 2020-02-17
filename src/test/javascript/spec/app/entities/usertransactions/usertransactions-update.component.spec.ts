import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MoneyChangerTestModule } from '../../../test.module';
import { UsertransactionsUpdateComponent } from 'app/entities/usertransactions/usertransactions-update.component';
import { UsertransactionsService } from 'app/entities/usertransactions/usertransactions.service';
import { Usertransactions } from 'app/shared/model/usertransactions.model';

describe('Component Tests', () => {
  describe('Usertransactions Management Update Component', () => {
    let comp: UsertransactionsUpdateComponent;
    let fixture: ComponentFixture<UsertransactionsUpdateComponent>;
    let service: UsertransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MoneyChangerTestModule],
        declarations: [UsertransactionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UsertransactionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsertransactionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsertransactionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Usertransactions(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Usertransactions();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MoneyChangerTestModule } from '../../../test.module';
import { UserBalanceUpdateComponent } from 'app/entities/userBalance/userBalance-update.component';
import { UserBalanceService } from 'app/entities/userBalance/userBalance.service';
import { UserBalance } from 'app/shared/model/userBalance.model';

describe('Component Tests', () => {
  describe('UserBalance Management Update Component', () => {
    let comp: UserBalanceUpdateComponent;
    let fixture: ComponentFixture<UserBalanceUpdateComponent>;
    let service: UserBalanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MoneyChangerTestModule],
        declarations: [UserBalanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserBalanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserBalanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserBalanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserBalance(123);
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
        const entity = new UserBalance();
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

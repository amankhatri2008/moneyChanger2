import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MoneyChangerTestModule } from '../../../test.module';
import { UserBalanceDetailComponent } from 'app/entities/user-balance/user-balance-detail.component';
import { UserBalance } from 'app/shared/model/user-balance.model';

describe('Component Tests', () => {
  describe('UserBalance Management Detail Component', () => {
    let comp: UserBalanceDetailComponent;
    let fixture: ComponentFixture<UserBalanceDetailComponent>;
    const route = ({ data: of({ userBalance: new UserBalance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MoneyChangerTestModule],
        declarations: [UserBalanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserBalanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserBalanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userBalance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userBalance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

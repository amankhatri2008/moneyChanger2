import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MoneyChangerTestModule } from '../../../test.module';
import { UsertransactionsDetailComponent } from 'app/entities/usertransactions/usertransactions-detail.component';
import { Usertransactions } from 'app/shared/model/usertransactions.model';

describe('Component Tests', () => {
  describe('Usertransactions Management Detail Component', () => {
    let comp: UsertransactionsDetailComponent;
    let fixture: ComponentFixture<UsertransactionsDetailComponent>;
    const route = ({ data: of({ usertransactions: new Usertransactions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MoneyChangerTestModule],
        declarations: [UsertransactionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UsertransactionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsertransactionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load usertransactions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usertransactions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

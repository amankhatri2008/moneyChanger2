import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUsertransactions, Usertransactions } from 'app/shared/model/usertransactions.model';
import { UsertransactionsService } from './usertransactions.service';
import { UsertransactionsComponent } from './usertransactions.component';
import { UsertransactionsDetailComponent } from './usertransactions-detail.component';
import { UsertransactionsUpdateComponent } from './usertransactions-update.component';

@Injectable({ providedIn: 'root' })
export class UsertransactionsResolve implements Resolve<IUsertransactions> {
  constructor(private service: UsertransactionsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsertransactions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((usertransactions: HttpResponse<Usertransactions>) => {
          if (usertransactions.body) {
            return of(usertransactions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Usertransactions());
  }
}

export const usertransactionsRoute: Routes = [
  {
    path: '',
    component: UsertransactionsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Usertransactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UsertransactionsDetailComponent,
    resolve: {
      usertransactions: UsertransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Usertransactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UsertransactionsUpdateComponent,
    resolve: {
      usertransactions: UsertransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Usertransactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UsertransactionsUpdateComponent,
    resolve: {
      usertransactions: UsertransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Usertransactions'
    },
    canActivate: [UserRouteAccessService]
  }
];

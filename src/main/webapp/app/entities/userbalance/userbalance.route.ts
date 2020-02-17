import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserbalance, Userbalance } from 'app/shared/model/userbalance.model';
import { UserbalanceService } from './userbalance.service';
import { UserbalanceComponent } from './userbalance.component';
import { UserbalanceDetailComponent } from './userbalance-detail.component';
import { UserbalanceUpdateComponent } from './userbalance-update.component';

@Injectable({ providedIn: 'root' })
export class UserbalanceResolve implements Resolve<IUserbalance> {
  constructor(private service: UserbalanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserbalance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userbalance: HttpResponse<Userbalance>) => {
          if (userbalance.body) {
            return of(userbalance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Userbalance());
  }
}

export const userbalanceRoute: Routes = [
  {
    path: '',
    component: UserbalanceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Userbalances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserbalanceDetailComponent,
    resolve: {
      userbalance: UserbalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userbalances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserbalanceUpdateComponent,
    resolve: {
      userbalance: UserbalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userbalances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserbalanceUpdateComponent,
    resolve: {
      userbalance: UserbalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userbalances'
    },
    canActivate: [UserRouteAccessService]
  }
];

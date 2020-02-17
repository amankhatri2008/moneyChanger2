import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserBalance, UserBalance } from 'app/shared/model/user-balance.model';
import { UserBalanceService } from './user-balance.service';
import { UserBalanceComponent } from './user-balance.component';
import { UserBalanceDetailComponent } from './user-balance-detail.component';
import { UserBalanceUpdateComponent } from './user-balance-update.component';

@Injectable({ providedIn: 'root' })
export class UserBalanceResolve implements Resolve<IUserBalance> {
  constructor(private service: UserBalanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserBalance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userBalance: HttpResponse<UserBalance>) => {
          if (userBalance.body) {
            return of(userBalance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserBalance());
  }
}

export const userBalanceRoute: Routes = [
  {
    path: '',
    component: UserBalanceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'UserBalances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserBalanceDetailComponent,
    resolve: {
      userBalance: UserBalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserBalances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserBalanceUpdateComponent,
    resolve: {
      userBalance: UserBalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserBalances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserBalanceUpdateComponent,
    resolve: {
      userBalance: UserBalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserBalances'
    },
    canActivate: [UserRouteAccessService]
  }
];

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserBalance } from 'app/shared/model/user-balance.model';

@Component({
  selector: 'jhi-user-balance-detail',
  templateUrl: './user-balance-detail.component.html'
})
export class UserBalanceDetailComponent implements OnInit {
  userBalance: IUserBalance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userBalance }) => {
      this.userBalance = userBalance;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

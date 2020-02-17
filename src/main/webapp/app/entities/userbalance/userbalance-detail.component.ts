import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserbalance } from 'app/shared/model/userbalance.model';

@Component({
  selector: 'jhi-userbalance-detail',
  templateUrl: './userbalance-detail.component.html'
})
export class UserbalanceDetailComponent implements OnInit {
  userbalance: IUserbalance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userbalance }) => {
      this.userbalance = userbalance;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsertransactions } from 'app/shared/model/usertransactions.model';

@Component({
  selector: 'jhi-usertransactions-detail',
  templateUrl: './usertransactions-detail.component.html'
})
export class UsertransactionsDetailComponent implements OnInit {
  usertransactions: IUsertransactions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usertransactions }) => {
      this.usertransactions = usertransactions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

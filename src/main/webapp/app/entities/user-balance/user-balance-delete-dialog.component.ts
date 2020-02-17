import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserBalance } from 'app/shared/model/user-balance.model';
import { UserBalanceService } from './user-balance.service';

@Component({
  templateUrl: './user-balance-delete-dialog.component.html'
})
export class UserBalanceDeleteDialogComponent {
  userBalance?: IUserBalance;

  constructor(
    protected userBalanceService: UserBalanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userBalanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userBalanceListModification');
      this.activeModal.close();
    });
  }
}

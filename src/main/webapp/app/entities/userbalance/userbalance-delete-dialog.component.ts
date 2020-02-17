import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserbalance } from 'app/shared/model/userbalance.model';
import { UserbalanceService } from './userbalance.service';

@Component({
  templateUrl: './userbalance-delete-dialog.component.html'
})
export class UserbalanceDeleteDialogComponent {
  userbalance?: IUserbalance;

  constructor(
    protected userbalanceService: UserbalanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userbalanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userbalanceListModification');
      this.activeModal.close();
    });
  }
}

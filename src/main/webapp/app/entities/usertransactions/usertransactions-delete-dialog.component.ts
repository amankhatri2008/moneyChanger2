import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsertransactions } from 'app/shared/model/usertransactions.model';
import { UsertransactionsService } from './usertransactions.service';

@Component({
  templateUrl: './usertransactions-delete-dialog.component.html'
})
export class UsertransactionsDeleteDialogComponent {
  usertransactions?: IUsertransactions;

  constructor(
    protected usertransactionsService: UsertransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usertransactionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('usertransactionsListModification');
      this.activeModal.close();
    });
  }
}

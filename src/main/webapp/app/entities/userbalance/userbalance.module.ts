import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneyChangerSharedModule } from 'app/shared/shared.module';
import { UserbalanceComponent } from './userbalance.component';
import { UserbalanceDetailComponent } from './userbalance-detail.component';
import { UserbalanceUpdateComponent } from './userbalance-update.component';
import { UserbalanceDeleteDialogComponent } from './userbalance-delete-dialog.component';
import { userbalanceRoute } from './userbalance.route';

@NgModule({
  imports: [MoneyChangerSharedModule, RouterModule.forChild(userbalanceRoute)],
  declarations: [UserbalanceComponent, UserbalanceDetailComponent, UserbalanceUpdateComponent, UserbalanceDeleteDialogComponent],
  entryComponents: [UserbalanceDeleteDialogComponent]
})
export class MoneyChangerUserbalanceModule {}

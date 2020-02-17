import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneyChangerSharedModule } from 'app/shared/shared.module';
import { UserBalanceComponent } from './user-balance.component';
import { UserBalanceDetailComponent } from './user-balance-detail.component';
import { UserBalanceUpdateComponent } from './user-balance-update.component';
import { UserBalanceDeleteDialogComponent } from './user-balance-delete-dialog.component';
import { userBalanceRoute } from './user-balance.route';

@NgModule({
  imports: [MoneyChangerSharedModule, RouterModule.forChild(userBalanceRoute)],
  declarations: [UserBalanceComponent, UserBalanceDetailComponent, UserBalanceUpdateComponent, UserBalanceDeleteDialogComponent],
  entryComponents: [UserBalanceDeleteDialogComponent]
})
export class MoneyChangerUserBalanceModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneyChangerSharedModule } from 'app/shared/shared.module';
import { UsertransactionsComponent } from './usertransactions.component';
import { UsertransactionsDetailComponent } from './usertransactions-detail.component';
import { UsertransactionsUpdateComponent } from './usertransactions-update.component';
import { UsertransactionsDeleteDialogComponent } from './usertransactions-delete-dialog.component';
import { usertransactionsRoute } from './usertransactions.route';

@NgModule({
  imports: [MoneyChangerSharedModule, RouterModule.forChild(usertransactionsRoute)],
  declarations: [
    UsertransactionsComponent,
    UsertransactionsDetailComponent,
    UsertransactionsUpdateComponent,
    UsertransactionsDeleteDialogComponent
  ],
  entryComponents: [UsertransactionsDeleteDialogComponent]
})
export class MoneyChangerUsertransactionsModule {}

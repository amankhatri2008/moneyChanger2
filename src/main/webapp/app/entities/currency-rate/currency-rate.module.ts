import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneyChangerSharedModule } from 'app/shared/shared.module';
import { CurrencyRateComponent } from './currency-rate.component';
import { CurrencyRateDetailComponent } from './currency-rate-detail.component';
import { CurrencyRateUpdateComponent } from './currency-rate-update.component';
import { CurrencyRateDeleteDialogComponent } from './currency-rate-delete-dialog.component';
import { currencyRateRoute } from './currency-rate.route';

@NgModule({
  imports: [MoneyChangerSharedModule, RouterModule.forChild(currencyRateRoute)],
  declarations: [CurrencyRateComponent, CurrencyRateDetailComponent, CurrencyRateUpdateComponent, CurrencyRateDeleteDialogComponent],
  entryComponents: [CurrencyRateDeleteDialogComponent]
})
export class MoneyChangerCurrencyRateModule {}

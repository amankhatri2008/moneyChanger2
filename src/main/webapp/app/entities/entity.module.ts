import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'currency-rate',
        loadChildren: () => import('./currency-rate/currency-rate.module').then(m => m.MoneyChangerCurrencyRateModule)
      },
      {
        path: 'usertransactions',
        loadChildren: () => import('./usertransactions/usertransactions.module').then(m => m.MoneyChangerUsertransactionsModule)
      },
      {
        path: 'user-balance',
        loadChildren: () => import('./user-balance/user-balance.module').then(m => m.MoneyChangerUserBalanceModule)
      },
      {
        path: 'userbalance',
        loadChildren: () => import('./userbalance/userbalance.module').then(m => m.MoneyChangerUserbalanceModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MoneyChangerEntityModule {}

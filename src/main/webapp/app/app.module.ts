import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MoneyChangerSharedModule } from 'app/shared/shared.module';
import { MoneyChangerCoreModule } from 'app/core/core.module';
import { MoneyChangerAppRoutingModule } from './app-routing.module';
import { MoneyChangerHomeModule } from './home/home.module';
import { MoneyChangerEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    MoneyChangerSharedModule,
    MoneyChangerCoreModule,
    MoneyChangerHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MoneyChangerEntityModule,
    MoneyChangerAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class MoneyChangerAppModule {}

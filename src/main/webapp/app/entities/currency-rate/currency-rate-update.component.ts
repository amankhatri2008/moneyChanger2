import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICurrencyRate, CurrencyRate } from 'app/shared/model/currency-rate.model';
import { CurrencyRateService } from './currency-rate.service';

@Component({
  selector: 'jhi-currency-rate-update',
  templateUrl: './currency-rate-update.component.html'
})
export class CurrencyRateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    currency: [null, [Validators.required]],
    buyRate: [null, [Validators.required]],
    sellRate: [null, [Validators.required]]
  });

  constructor(protected currencyRateService: CurrencyRateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currencyRate }) => {
      this.updateForm(currencyRate);
    });
  }

  updateForm(currencyRate: ICurrencyRate): void {
    this.editForm.patchValue({
      id: currencyRate.id,
      currency: currencyRate.currency,
      buyRate: currencyRate.buyRate,
      sellRate: currencyRate.sellRate
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const currencyRate = this.createFromForm();
    if (currencyRate.id !== undefined) {
      this.subscribeToSaveResponse(this.currencyRateService.update(currencyRate));
    } else {
      this.subscribeToSaveResponse(this.currencyRateService.create(currencyRate));
    }
  }

  private createFromForm(): ICurrencyRate {
    return {
      ...new CurrencyRate(),
      id: this.editForm.get(['id'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      buyRate: this.editForm.get(['buyRate'])!.value,
      sellRate: this.editForm.get(['sellRate'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrencyRate>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}

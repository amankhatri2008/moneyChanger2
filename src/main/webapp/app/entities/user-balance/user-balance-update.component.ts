import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IUserBalance, UserBalance } from 'app/shared/model/user-balance.model';
import { UserBalanceService } from './user-balance.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-user-balance-update',
  templateUrl: './user-balance-update.component.html'
})
export class UserBalanceUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    currency: [null, [Validators.required]],
    balance: [null, [Validators.required]],
    nameId: [null, Validators.required]
  });

  constructor(
    protected userBalanceService: UserBalanceService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userBalance }) => {
      this.updateForm(userBalance);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(userBalance: IUserBalance): void {
    this.editForm.patchValue({
      id: userBalance.id,
      userId: userBalance.userId,
      currency: userBalance.currency,
      balance: userBalance.balance,
      nameId: userBalance.nameId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userBalance = this.createFromForm();
    if (userBalance.id !== undefined) {
      this.subscribeToSaveResponse(this.userBalanceService.update(userBalance));
    } else {
      this.subscribeToSaveResponse(this.userBalanceService.create(userBalance));
    }
  }

  private createFromForm(): IUserBalance {
    return {
      ...new UserBalance(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      nameId: this.editForm.get(['nameId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserBalance>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}

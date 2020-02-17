import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IUsertransactions, Usertransactions } from 'app/shared/model/usertransactions.model';
import { UsertransactionsService } from './usertransactions.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-usertransactions-update',
  templateUrl: './usertransactions-update.component.html'
})
export class UsertransactionsUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    from_quantity: [null, [Validators.required]],
    toQuantity: [null, [Validators.required]],
    action: [],
    userId: [null, Validators.required]
  });

  constructor(
    protected usertransactionsService: UsertransactionsService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usertransactions }) => {
      this.updateForm(usertransactions);

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

  updateForm(usertransactions: IUsertransactions): void {
    this.editForm.patchValue({
      id: usertransactions.id,
      from_quantity: usertransactions.from_quantity,
      toQuantity: usertransactions.toQuantity,
      action: usertransactions.action,
      userId: usertransactions.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usertransactions = this.createFromForm();
    if (usertransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.usertransactionsService.update(usertransactions));
    } else {
      this.subscribeToSaveResponse(this.usertransactionsService.create(usertransactions));
    }
  }

  private createFromForm(): IUsertransactions {
    return {
      ...new Usertransactions(),
      id: this.editForm.get(['id'])!.value,
      from_quantity: this.editForm.get(['from_quantity'])!.value,
      toQuantity: this.editForm.get(['toQuantity'])!.value,
      action: this.editForm.get(['action'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsertransactions>>): void {
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

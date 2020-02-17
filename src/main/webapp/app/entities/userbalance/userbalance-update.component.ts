import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IUserbalance, Userbalance } from 'app/shared/model/userbalance.model';
import { UserbalanceService } from './userbalance.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-userbalance-update',
  templateUrl: './userbalance-update.component.html'
})
export class UserbalanceUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    balance: [null, [Validators.required]],
    nameId: [null, Validators.required]
  });

  constructor(
    protected userbalanceService: UserbalanceService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userbalance }) => {
      this.updateForm(userbalance);

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

  updateForm(userbalance: IUserbalance): void {
    this.editForm.patchValue({
      id: userbalance.id,
      balance: userbalance.balance,
      nameId: userbalance.nameId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userbalance = this.createFromForm();
    if (userbalance.id !== undefined) {
      this.subscribeToSaveResponse(this.userbalanceService.update(userbalance));
    } else {
      this.subscribeToSaveResponse(this.userbalanceService.create(userbalance));
    }
  }

  private createFromForm(): IUserbalance {
    return {
      ...new Userbalance(),
      id: this.editForm.get(['id'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      nameId: this.editForm.get(['nameId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserbalance>>): void {
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

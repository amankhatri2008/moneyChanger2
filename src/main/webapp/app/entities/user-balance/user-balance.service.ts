import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserBalance } from 'app/shared/model/user-balance.model';

type EntityResponseType = HttpResponse<IUserBalance>;
type EntityArrayResponseType = HttpResponse<IUserBalance[]>;

@Injectable({ providedIn: 'root' })
export class UserBalanceService {
  public resourceUrl = SERVER_API_URL + 'api/user-balances';

  constructor(protected http: HttpClient) {}

  create(userBalance: IUserBalance): Observable<EntityResponseType> {
    return this.http.post<IUserBalance>(this.resourceUrl, userBalance, { observe: 'response' });
  }

  update(userBalance: IUserBalance): Observable<EntityResponseType> {
    return this.http.put<IUserBalance>(this.resourceUrl, userBalance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserBalance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserBalance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

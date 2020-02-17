import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserbalance } from 'app/shared/model/userbalance.model';

type EntityResponseType = HttpResponse<IUserbalance>;
type EntityArrayResponseType = HttpResponse<IUserbalance[]>;

@Injectable({ providedIn: 'root' })
export class UserbalanceService {
  public resourceUrl = SERVER_API_URL + 'api/userbalances';

  constructor(protected http: HttpClient) {}

  create(userbalance: IUserbalance): Observable<EntityResponseType> {
    return this.http.post<IUserbalance>(this.resourceUrl, userbalance, { observe: 'response' });
  }

  update(userbalance: IUserbalance): Observable<EntityResponseType> {
    return this.http.put<IUserbalance>(this.resourceUrl, userbalance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserbalance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserbalance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

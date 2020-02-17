import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUsertransactions } from 'app/shared/model/usertransactions.model';

type EntityResponseType = HttpResponse<IUsertransactions>;
type EntityArrayResponseType = HttpResponse<IUsertransactions[]>;

@Injectable({ providedIn: 'root' })
export class UsertransactionsService {
  public resourceUrl = SERVER_API_URL + 'api/usertransactions';

  constructor(protected http: HttpClient) {}

  create(usertransactions: IUsertransactions): Observable<EntityResponseType> {
    return this.http.post<IUsertransactions>(this.resourceUrl, usertransactions, { observe: 'response' });
  }

  update(usertransactions: IUsertransactions): Observable<EntityResponseType> {
    return this.http.put<IUsertransactions>(this.resourceUrl, usertransactions, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUsertransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUsertransactions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

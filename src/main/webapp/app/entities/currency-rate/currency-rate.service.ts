import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICurrencyRate } from 'app/shared/model/currency-rate.model';

type EntityResponseType = HttpResponse<ICurrencyRate>;
type EntityArrayResponseType = HttpResponse<ICurrencyRate[]>;

@Injectable({ providedIn: 'root' })
export class CurrencyRateService {
  public resourceUrl = SERVER_API_URL + 'api/currency-rates';

  constructor(protected http: HttpClient) {}

  create(currencyRate: ICurrencyRate): Observable<EntityResponseType> {
    return this.http.post<ICurrencyRate>(this.resourceUrl, currencyRate, { observe: 'response' });
  }

  update(currencyRate: ICurrencyRate): Observable<EntityResponseType> {
    return this.http.put<ICurrencyRate>(this.resourceUrl, currencyRate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurrencyRate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurrencyRate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

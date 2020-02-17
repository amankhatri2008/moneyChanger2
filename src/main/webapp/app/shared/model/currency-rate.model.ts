export interface ICurrencyRate {
  id?: number;
  currency?: string;
  buyRate?: number;
  sellRate?: number;
}

export class CurrencyRate implements ICurrencyRate {
  constructor(public id?: number, public currency?: string, public buyRate?: number, public sellRate?: number) {}
}

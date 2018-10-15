export interface ITcr {
  id?: number;
  tcrHash?: string;
  tcrName?: string;
  content?: string;
  bondingCurve?: number;
}

export const defaultValue: Readonly<ITcr> = {};

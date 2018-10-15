export interface IIdentity {
  id?: number;
  chain?: string;
  identityAddress?: string;
}

export const defaultValue: Readonly<IIdentity> = {};

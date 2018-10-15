export interface IIdentityProfile {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  redditUrl?: string;
}

export const defaultValue: Readonly<IIdentityProfile> = {};

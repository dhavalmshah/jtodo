import dayjs from 'dayjs';
import { IUserAttributes } from 'app/shared/model/user-attributes.model';

export interface IProject {
  id?: number;
  name?: string;
  description?: string | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  color?: string | null;
  icon?: string | null;
  owner?: IUserAttributes | null;
  members?: IUserAttributes[] | null;
}

export const defaultValue: Readonly<IProject> = {};

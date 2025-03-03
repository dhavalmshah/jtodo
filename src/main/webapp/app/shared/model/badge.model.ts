import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { BadgeType } from 'app/shared/model/enumerations/badge-type.model';

export interface IBadge {
  id?: number;
  name?: string;
  description?: string;
  image?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  type?: keyof typeof BadgeType;
  criteria?: string;
  users?: IUser[] | null;
}

export const defaultValue: Readonly<IBadge> = {};

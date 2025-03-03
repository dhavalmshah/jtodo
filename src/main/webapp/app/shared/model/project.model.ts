import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITodo } from 'app/shared/model/todo.model';

export interface IProject {
  id?: number;
  name?: string;
  description?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  owner?: IUser | null;
  members?: IUser[] | null;
  todos?: ITodo[] | null;
}

export const defaultValue: Readonly<IProject> = {};

import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITodo } from 'app/shared/model/todo.model';
import { IBadge } from 'app/shared/model/badge.model';
import { IProject } from 'app/shared/model/project.model';

export interface IUserAttributes {
  id?: number;
  name?: string | null;
  email?: string | null;
  emailVerified?: dayjs.Dayjs | null;
  imageContentType?: string | null;
  image?: string | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  password?: string | null;
  level?: number;
  points?: number;
  user?: IUser | null;
  assignedTodos?: ITodo[] | null;
  badges?: IBadge[] | null;
  projectMembers?: IProject[] | null;
}

export const defaultValue: Readonly<IUserAttributes> = {};

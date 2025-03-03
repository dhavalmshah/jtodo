import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITodo } from 'app/shared/model/todo.model';

export interface IAttachment {
  id?: number;
  name?: string;
  file?: string;
  fileContentType?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  uploader?: IUser | null;
  todo?: ITodo | null;
}

export const defaultValue: Readonly<IAttachment> = {};

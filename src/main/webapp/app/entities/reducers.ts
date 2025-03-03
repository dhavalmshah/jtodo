import project from 'app/entities/project/project.reducer';
import todo from 'app/entities/todo/todo.reducer';
import tag from 'app/entities/tag/tag.reducer';
import attachment from 'app/entities/attachment/attachment.reducer';
import badge from 'app/entities/badge/badge.reducer';
import comment from 'app/entities/comment/comment.reducer';
import notification from 'app/entities/notification/notification.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  project,
  todo,
  tag,
  attachment,
  badge,
  comment,
  notification,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

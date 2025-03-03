import React from 'react';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Project from './project';
import Todo from './todo';
import Tag from './tag';
import Attachment from './attachment';
import Badge from './badge';
import Comment from './comment';
import Notification from './notification';
import { Route } from 'react-router';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="project/*" element={<Project />} />
        <Route path="todo/*" element={<Todo />} />
        <Route path="tag/*" element={<Tag />} />
        <Route path="attachment/*" element={<Attachment />} />
        <Route path="badge/*" element={<Badge />} />
        <Route path="comment/*" element={<Comment />} />
        <Route path="notification/*" element={<Notification />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};

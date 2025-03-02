import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserAttributes from './user-attributes';
import UserAttributesDetail from './user-attributes-detail';
import UserAttributesUpdate from './user-attributes-update';
import UserAttributesDeleteDialog from './user-attributes-delete-dialog';

const UserAttributesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserAttributes />} />
    <Route path="new" element={<UserAttributesUpdate />} />
    <Route path=":id">
      <Route index element={<UserAttributesDetail />} />
      <Route path="edit" element={<UserAttributesUpdate />} />
      <Route path="delete" element={<UserAttributesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserAttributesRoutes;

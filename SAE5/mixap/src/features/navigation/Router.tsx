import { useEffect } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import { RxColOp } from '../../db/types';
import { useActivity } from '../../hooks/useActivity';

import useAuth from '../../hooks/useAuth';
import useInstall from '../../hooks/useInstall';
import useLogger from '../../hooks/useLogger';
import Activity from '../activity/Activity';
import ActivityMenu from '../activity/ActivityMenu';
import Dashboard from '../dashboard/Dashboard';
import Editor from '../editor/Editor';
import Home from '../home/Home';
import Player from '../player/Player';
import Share from '../share/Share';
// import useScreenOrientation from '../../hooks/useScreenOrientation';

export default function Router() {
  const log = useLogger('Router');

  log.debug('Render');

  const { onRxColActivity } = useActivity();
  const { onRxColUser } = useAuth();

  useInstall();
  // useMemory();
  // useScreenOrientation();

  useEffect(() => {
    // get users' activities and auras
    onRxColActivity(RxColOp.FindAll, {
      /** */
    });
    onRxColUser(RxColOp.FindOne, {
      /** */
    });
  }, [onRxColActivity, onRxColUser]);

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path='/about'
          element={<About />}
        />
        <Route
          path='/edit-activity/:id/:ancre'
          element={<Editor />}
        />
        <Route
          path='/view-activity/:id/:ancre'
          element={<Activity />}
        />
        <Route
          path='/play-activity/:id/:ancre'
          element={<Player />}
        />
        <Route
          path='/share-activity/:userId/:activityId/:token'
          element={<Share />}
        />
        <Route
          path='/dashboard'
          element={<Dashboard />}
        />
        <Route
          path='/library'
          element={<Home />}
        />
        <Route
          path='/'
          element={<Home />}
        />
      </Routes>

      <ActivityMenu />
    </BrowserRouter>
  );
}

function About() {
  return (
    <div>
      <h2>About</h2>
    </div>
  );
}

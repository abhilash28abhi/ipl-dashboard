import './App.css';
import TeamPage from './pages/TeamPage';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import MatchPage from './pages/MatchPage';
import HomePage from './pages/HomePage';

const App = (props) => {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/teams/:teamName/matches/:year"
          render={({ match }) => {
                  return <MatchPage key={match.params.year} />;
              }}/>
          <Route path="/teams/:teamName" 
          render={({ match }) => {
                  return <TeamPage key={match.params.teamName} />;
              }}/>
          <Route path="/">
            <HomePage/>
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;

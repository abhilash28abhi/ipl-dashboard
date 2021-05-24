import { PureComponent, React} from 'react';
import { MatchSmallCard } from '../components/MatchSmallCard';
import { withRouter } from 'react-router-dom';
import './MatchPage.css';
import { YearSelector } from '../components/YearSelector';
import { Logo } from '../components/Logo';

class MatchPage extends PureComponent {

    constructor (props) {
        super(props);
        this.state = {
            matches: [],
            teamName: null,
            year:null
        }
    }

    async componentDidMount() {
        const teamName = this.props.match.params.teamName;
        const year = this.props.match.params.year;
        const response = await fetch(`http://localhost:8080/team/${teamName}/matches?year=${year}`);
        const json = await response.json();
        //console.log(json);
        this.setState({matches:json, teamName:teamName, year:year});
    }

    render () {
        return (
            <div>
                <Logo/>
                <div className="MatchPage">
                <div className="year-selector">
                    <h3> Select Year </h3>
                    <YearSelector teamName={this.state.teamName}/>
                </div>  
                    <div>
                    <h1 className="page-heading">{this.state.teamName} matches in {this.state.year}</h1>
                        {this.state.matches.map(match => <MatchSmallCard key={match.id} teamName={this.state.teamName} match={match} />)}
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(MatchPage);
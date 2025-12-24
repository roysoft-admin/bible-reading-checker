import { useState } from 'react';
import WelcomeScreen from './components/WelcomeScreen';
import HomeScreen from './components/HomeScreen';
import ReadingPlanScreen from './components/ReadingPlanScreen';
import ProgressScreen from './components/ProgressScreen';
import SettingsScreen from './components/SettingsScreen';

export default function App() {
  const [currentScreen, setCurrentScreen] = useState<'welcome' | 'home' | 'plan' | 'progress' | 'settings'>('welcome');
  const [selectedPlan, setSelectedPlan] = useState<string>('');

  const handleStartReading = (plan: string) => {
    setSelectedPlan(plan);
    setCurrentScreen('home');
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-[#f5f3ef] py-8 px-4">
      {/* Mobile Device Frame */}
      <div className="w-full max-w-[390px] h-[844px] bg-[#faf8f5] rounded-[40px] shadow-2xl overflow-hidden relative">
        {/* Screen Content */}
        <div className="h-full overflow-auto">
          {currentScreen === 'welcome' && (
            <WelcomeScreen onStartReading={handleStartReading} />
          )}
          {currentScreen === 'home' && (
            <HomeScreen 
              selectedPlan={selectedPlan}
              onNavigate={setCurrentScreen}
            />
          )}
          {currentScreen === 'plan' && (
            <ReadingPlanScreen onNavigate={setCurrentScreen} />
          )}
          {currentScreen === 'progress' && (
            <ProgressScreen onNavigate={setCurrentScreen} />
          )}
          {currentScreen === 'settings' && (
            <SettingsScreen onNavigate={setCurrentScreen} />
          )}
        </div>
      </div>

      {/* Screen Selector (for demo purposes) */}
      <div className="fixed bottom-8 left-1/2 -translate-x-1/2 bg-white/90 backdrop-blur-sm rounded-full px-6 py-3 shadow-lg flex gap-3">
        <button
          onClick={() => setCurrentScreen('welcome')}
          className={`px-4 py-2 rounded-full transition-colors ${
            currentScreen === 'welcome' ? 'bg-[#a5b4a1] text-white' : 'bg-gray-100 text-gray-600'
          }`}
        >
          Welcome
        </button>
        <button
          onClick={() => setCurrentScreen('home')}
          className={`px-4 py-2 rounded-full transition-colors ${
            currentScreen === 'home' ? 'bg-[#a5b4a1] text-white' : 'bg-gray-100 text-gray-600'
          }`}
        >
          Home
        </button>
        <button
          onClick={() => setCurrentScreen('plan')}
          className={`px-4 py-2 rounded-full transition-colors ${
            currentScreen === 'plan' ? 'bg-[#a5b4a1] text-white' : 'bg-gray-100 text-gray-600'
          }`}
        >
          Plan
        </button>
        <button
          onClick={() => setCurrentScreen('progress')}
          className={`px-4 py-2 rounded-full transition-colors ${
            currentScreen === 'progress' ? 'bg-[#a5b4a1] text-white' : 'bg-gray-100 text-gray-600'
          }`}
        >
          Progress
        </button>
        <button
          onClick={() => setCurrentScreen('settings')}
          className={`px-4 py-2 rounded-full transition-colors ${
            currentScreen === 'settings' ? 'bg-[#a5b4a1] text-white' : 'bg-gray-100 text-gray-600'
          }`}
        >
          Settings
        </button>
      </div>
    </div>
  );
}

using CryptoNews.Database;
using CryptoNews.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Xamarin.Forms;

namespace CryptoNews
{
    public partial class App : Application
    {
          private static CryptoNewsDB _database;
        public static CryptoNewsDB database
        {
            get
            {
                if (_database == null)
                {
                    _database = new CryptoNewsDB(DependencyService.Get<IFileHelper>().GetLocalFilePath("CryptoNews.db3"));
                }
                return _database;
            }
        }
               
        public App()
        {
            InitializeComponent();
            Initasync();

            MainPage = new CryptoNews.MainPage();
        }
        public  async void Initasync()
        {
            var postsRepository = new PostsRepository();
            await postsRepository.GetAllPostsAsync();
        }

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}

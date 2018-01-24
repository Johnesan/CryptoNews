using CryptoNews.Models;
using CryptoNews.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Xamarin.Forms;

namespace CryptoNews.ViewModels
{
    class BlogPostsViewModel : INotifyPropertyChanged
    {
        public ICommand  RefreshCommand { get; set; }

        private List<BlogPost> _blogPosts;
        public List<BlogPost> BlogPosts
        {
            get { return _blogPosts; }
            set
            {
                _blogPosts = value;
                OnPropertyChanged();
            }
        }

        private bool _isBusy;
        public bool IsBusy
        {
            get { return _isBusy; }
            set
            {
                _isBusy = value;
                OnPropertyChanged();
            }
        }

        private INavigation Navigation;
      
            public BlogPostsViewModel(INavigation _Navigation)
        {
            RefreshCommand = new Command(RefreshAction);
            Navigation = _Navigation;
            IsBusy = true;
            BlogPosts = App.database.GetAllBlogPosts();
            InitAsync();
        }

        private async Task InitAsync()
        {
            var service = new PostsRepository();
            var UpdatedPosts = await service.GetAllPostsAsync();
            App.database.AddUpdatedBlogPosts(UpdatedPosts);
            BlogPosts = App.database.GetAllBlogPosts();
            IsBusy = false;
        }

        public async void RefreshAction()
        {
            await InitAsync();
        }

        public event PropertyChangedEventHandler PropertyChanged;
        public virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged.Invoke(this, new PropertyChangedEventArgs(propertyName));
            }
        }

    }
}
